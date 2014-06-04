select concat(Tournaments.name,'  ',Matches.date) as tournament 
from Tournaments, Matches 
where Matches.offline = 'TRUE' 
    and Tournaments.tournament_id = Matches.tournament
    and ((Matches.scoreA = 4 and Matches.scoreB =0) or (Matches.scoreA = 0 and Matches.scoreB =4));