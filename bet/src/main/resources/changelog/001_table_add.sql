-- liquibase formatted sql
-- changeset betpawa:liqubase run verification
-- validchecksum: ANY

CREATE TABLE IF NOT EXISTS active_bets (
    active_bet_id int(11) PRIMARY KEY NOT NULL,
    account_id int(11) NOT NULL,
    bet_id int(11) NOT NULL,
    event_date DATE NOT NULL,
    odd DECIMAL NOT NULL,
    outcome VARCHAR(55),
    possible_win DECIMAL ,
    status VARCHAR(55)
);

CREATE TABLE IF NOT EXISTS bet_slips  (
    bet_id int(11) PRIMARY KEY NOT NULL,
    event_date DATE,
    event_name VARCHAR(55),
    odd DECIMAL,
    outcome VARCHAR(55)
);