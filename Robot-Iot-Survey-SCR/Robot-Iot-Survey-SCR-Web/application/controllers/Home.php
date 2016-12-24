<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//session_start(); //we need to call PHP's session object to access it through CI
class Home extends CI_Controller {

 function __construct()
 {
	 
   parent::__construct();
   $this->load->model('user','',TRUE);
   $this->load->model('robot','',TRUE);
 }

 function index()
 {
   if($this->session->userdata('logged_in'))
   {
     $session_data = $this->session->userdata('logged_in');
     $data['username'] = $session_data['username'];
	 $data['userRobot'] = $this->userRobot();
	 //
	 $robotData = $this->robotData($data['userRobot']);
	 if($robotData){
		  foreach($robotData as $row){
			  $data['app_id'] = $row->app_id;
			  $data['app_key'] = $row->app_key;
			  $data['app_secret'] = $row->app_secret;
			  $data['alias'] = $row->alias;
			  //return $row->robot_name;
		  }
		 
	 }
	 
	 //
	 
	 
	 //var_dump($data);
	 //die();
     $this->load->view('home_view', $data);
   }
   else
   {
     //If no session, redirect to login page
     redirect('login', 'refresh');
	 //echo "no see";
   }
 }

 function logout()
 {
   $this->session->unset_userdata('logged_in');
   session_destroy();
   redirect('home', 'refresh');
 }
 
 function userRobot(){
	 $session_data = $this->session->userdata('logged_in');
	 $result = $this->user->getUserRobot($session_data['username']);
	 //var_dump($result);
	 //die();
	 if($result){
		  foreach($result as $row){
			  //$data['userRobot'] = $row->robot_name;
			  return $row->robot_name;
		  }
		 
	 }
   
 }
 
 function robotData($robot_name){
	 $robotData = $this->robot->getRobot($robot_name);
	 return $robotData;
 }

}

?>