<?php
$dbhost = 'localhost:3306';
$dbuser = 'root';
$dbpass = 'admin';
$db = 'akeniligjerata';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass, $db);
if (!$conn) 
{
	die('Something went wrong! '.mysqli_connect_error());
}
// $sql = "select day,classnumber,classname,starttime,endtime from schedule";
$sql = "select * from schedule";
$retval = mysqli_query($conn , $sql);
if (!$retval) 
{
	die('Query couldnt be executed'.mysqli_connect_error());
}
$data = array();
while($row = mysqli_fetch_assoc($retval)) 
{
    $data[] = $row;
	// $data[$row['day']][$row['classnumber']][] = $row;
}

header('Content-Type: application/json');
echo json_encode($data,JSON_PRETTY_PRINT);

mysqli_close($conn);
