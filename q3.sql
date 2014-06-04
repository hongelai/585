select Tournaments.name,Tournaments.region,sum(prize_money) 
from Tournaments, Earnings   
where Tournaments.tournament_id = Earnings.tournament 
group by Earnings.tournament   
having sum(prize_money) >10000 
order by sum(prize_money) DESC;