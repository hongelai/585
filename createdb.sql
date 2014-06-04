create database if not exists ResultTracker CHARACTER SET utf8 COLLATE utf8_general_ci;
use ResultTracker;

create table Players
(
	player_id       int(4)       not null,
	tag             varchar(50)  not null,
	real_name       varchar(50)  not null,
	nationality	    char(2)      not null,
	birthday		date         not null,
	game_race		varchar(50)  not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE Players ADD PRIMARY KEY (player_id);

create table Teams
(
	team_id         int(3)       not null,
	name            varchar(50)  not null,
	founded	        date         not null,
	disbanded       date
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE Teams ADD PRIMARY KEY (team_id);


create table Members
(
	player           int(4) not null,
	team			 int(3) not null,
	start_date       date not null,
	end_date		 date
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE Members ADD PRIMARY KEY (player,start_date);
ALTER TABLE Members ADD FOREIGN KEY (player) REFERENCES Players(player_id);
ALTER TABLE Members ADD FOREIGN KEY (team) REFERENCES Teams(team_id);


create table Tournaments
(
	tournament_id     int(5)       not null,
	name              varchar(100) not null,
	region            char(2),
	major             char(5)      not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE Tournaments ADD PRIMARY KEY (tournament_id);


create table Matches
(
	match_id          int(5) not null,
	date              date   not null,
	tournament        int(5) not null,
	playerA           int(4) not null,
	playerB           int(4) not null,
	scoreA            int(1) not null,
	scoreB            int(1) not null,
	offline           char(5)not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE Matches ADD PRIMARY KEY (match_id);
ALTER TABLE Matches ADD FOREIGN KEY (tournament) REFERENCES Tournaments(tournament_id);
ALTER TABLE Matches ADD FOREIGN KEY (playerA) REFERENCES Players(player_id);
ALTER TABLE Matches ADD FOREIGN KEY (playerB) REFERENCES Players(player_id);

create table Earnings
(
	tournament        int(5) not null,
	player            int(4) not null,
	prize_money       int(6) not null,
	position          int(3) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE Earnings ADD PRIMARY KEY (tournament,player);
ALTER TABLE Earnings ADD FOREIGN KEY (tournament) REFERENCES Tournaments(tournament_id);
ALTER TABLE Earnings ADD FOREIGN KEY (player) REFERENCES Players(player_id);





