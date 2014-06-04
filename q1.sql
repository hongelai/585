select real_name, birthday 
from Players 
where nationality != 'KR' 
      and birthday like '%1985%';