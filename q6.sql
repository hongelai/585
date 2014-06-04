select Players.tag,Players.game_race 
from Players,Tournaments,Earnings 
where Players.player_id = Earnings.player and Tournaments.tournament_id = Earnings.tournament and position = 1
    and (Tournaments.region = 'KR' or Tournaments.region = 'AM' or Tournaments.region = 'EU') 
group by player 
having count(distinct region) = 3;