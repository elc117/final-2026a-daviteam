CREATE TABLE IF NOT EXISTS decks(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(60) NOT NULL,
	daily_new_limit int NOT NULL DEFAULT 10,
	daily_review_limit int NOT NULL DEFAULT 30,
	new_today int NOT NULL DEFAULT 0,
	review_today int NOT NULL DEFAULT 0,
	last_session_date DATE
);

CREATE TABLE IF NOT EXISTS cards(
	id BIGSERIAL PRIMARY KEY,
	deck_id BIGINT NOT NULL references decks(id) ON DELETE cascade,
	front TEXT NOT NULL,
	back TEXT NOT NULL,
	type VARCHAR(20) NOT NULL,
	status VARCHAR(30) NOT NULL,
	ease int NOT NULL DEFAULT 250,
	successful_reviews int NOT NULL DEFAULT 0,
	last_interval int NOT NULL DEFAULT 0,
	next_review DATE NOT NULL DEFAULT CURRENT_DATE
);