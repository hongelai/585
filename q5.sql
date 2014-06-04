select Players.tag,Players.real_name, MAX(Members.end_date) AS MostRecentDeparture 
from Players, Members,Teams 
where Members.player = Players.Player_id and Members.team = Teams.team_id
    and end_date is not null and Teams.name = 'ROOT Gaming' 
group by player_id 
having MAX(Members.end_date);