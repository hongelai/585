select Players.tag,Players.real_name,Players.nationality,Teams.name 
from Players, Teams,Members 
where Players.game_race = 'Z'
    and Members.player = Players.player_id and Members.team = Teams.team_id;