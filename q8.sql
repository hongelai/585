select Teams.name,Teams.founded, count(game_race)
from
(select from)


select Teams.name,Teams.founded,STUFF(select num_of_race from table1 where game_race='P') as val
from
(select  Teams.name,Teams.founded,Players.game_race,count(game_race) as num_of_race
 from Teams,Players,Members 
 where Teams.team_id = Members.team and Players.player_id = Members.player
    and disbanded is null 
 group by team,game_race
) as table1;

