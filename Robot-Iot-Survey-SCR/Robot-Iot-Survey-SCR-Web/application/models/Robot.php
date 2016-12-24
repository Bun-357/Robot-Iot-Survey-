<?php
Class Robot extends CI_Model
{
 /*function login($username, $password)
 {
   $this -> db -> select('id, username, password');
   $this -> db -> from('users');
   $this -> db -> where('username', $username);
   $this -> db -> where('password', MD5($password));
   $this -> db -> limit(1);

   $query = $this -> db -> get();

   if($query -> num_rows() == 1)
   {
     return $query->result();
   }
   else
   {
     return false;
   }
 }*/
 
 function getRobot($robot_name){
	 $this -> db -> select('robot_name,app_id,app_key,app_secret,alias');
	 $this -> db -> from('robot');
	 $this -> db -> where('robot_name', $robot_name);
	 $this -> db -> limit(1);
	 $query = $this -> db -> get();
	 
	 return $query->result();
	 
 }
}
?>