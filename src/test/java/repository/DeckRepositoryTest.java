package repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Deck;

class DeckRepositoryTest {
	private static Jdbi jdbi;
	private DeckRepository repository;

	@BeforeAll
	static void setupDatabase() throws Exception {
		String url = requireEnv("DB_URL");
		String user = requireEnv("DB_USER");
		String pass = requireEnv("DB_PASS");

		jdbi = Jdbi.create(url, user, pass);

		try (Connection conn = DriverManager.getConnection(url, user, pass);
			 Statement stmt = conn.createStatement()) {
			stmt.execute("""
				DROP TABLE IF EXISTS cards;
				DROP TABLE IF EXISTS decks;
			""");

			runSqlScript(stmt, "/db/schema.sql");
		}
	}

	@BeforeEach
	void setup() {
		repository = new DeckRepository();
		setField(repository, "jdbi", jdbi);

		jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM decks").execute());
	}

	@Test
	void schemaAcceptsInsert() {
		jdbi.useHandle(handle -> {
			int rows = handle.createUpdate("""
				INSERT INTO decks (name, daily_new_limit, daily_review_limit, new_today, review_today, last_session_date)
				VALUES (:name, :dailyNewLimit, :dailyReviewLimit, :newToday, :reviewToday, :lastSessionDate)
			""")
			.bind("name", "Test Deck")
			.bind("dailyNewLimit", 10)
			.bind("dailyReviewLimit", 30)
			.bind("newToday", 0)
			.bind("reviewToday", 0)
			.bind("lastSessionDate", LocalDate.now())
			.execute();

			assertEquals(1, rows);
		});
	}

	@Test
	void mapperReadsDeckCorrectly() {
		Long id = jdbi.withHandle(handle ->
			handle.createUpdate("""
				INSERT INTO decks (name, daily_new_limit, daily_review_limit, new_today, review_today, last_session_date)
				VALUES (:name, :dailyNewLimit, :dailyReviewLimit, :newToday, :reviewToday, :lastSessionDate)
			""")
			.bind("name", "Mapped Deck")
			.bind("dailyNewLimit", 10)
			.bind("dailyReviewLimit", 30)
			.bind("newToday", 2)
			.bind("reviewToday", 3)
			.bind("lastSessionDate", LocalDate.now())
			.executeAndReturnGeneratedKeys("id")
			.mapTo(Long.class)
			.one()
		);

		Deck deck = repository.findById(id).orElseThrow();

		assertEquals(10, deck.getNewCardLimit());
		assertEquals(30, deck.getReviewLimit());
		assertEquals(2, deck.getNewCardsToday());
		assertEquals(3, deck.getReviewedToday());
		assertEquals("Mapped Deck", readStringField(deck, "name"));
	}

	@Test
	void findAllReturnsSavedDecks() {
		jdbi.useHandle(handle -> {
			handle.createUpdate("""
				INSERT INTO decks (name, daily_new_limit, daily_review_limit, new_today, review_today, last_session_date)
				VALUES ('First Deck', 10, 30, 0, 0, CURRENT_DATE),
				       ('Second Deck', 5, 15, 1, 2, CURRENT_DATE)
			""").execute();
		});

		assertEquals(2, repository.findAll().size());
		assertTrue(repository.findAll().stream().anyMatch(deck -> "First Deck".equals(readStringField(deck, "name"))));
	}

	private static void setField(Object target, String fieldName, Object value) {
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String readStringField(Object target, String fieldName) {
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return (String) field.get(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String requireEnv(String key) {
		String value = System.getenv(key);
		if (value == null || value.isBlank()) {
			throw new IllegalStateException("Missing required environment variable: " + key);
		}
		return value;
	}

	private static void runSqlScript(Statement stmt, String resourcePath) throws Exception {
		try (InputStream in = DeckRepositoryTest.class.getResourceAsStream(resourcePath)) {
			if (in == null) {
				throw new IllegalStateException("Missing schema resource: " + resourcePath);
			}

			String sql = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));

			for (String command : sql.split(";")) {
				String trimmed = command.trim();
				if (!trimmed.isEmpty()) {
					stmt.execute(trimmed);
				}
			}
		}
	}
}
