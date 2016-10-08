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
if (isset($_REQUEST["t"]))
{
	$requesttype=intval($_REQUEST["t"]);
	switch ($requesttype)
	{
		case 0:
			$classroom = intval($_GET["classroom"]);
			$sql = "select * from comments order by reg_date desc";
			$retval = mysqli_query($conn , $sql);
			if (!$retval) 
			{
				die('Query couldnt be executed'.mysqli_connect_error());
			}
			$data = array();
			while($row = mysqli_fetch_assoc($retval)) 
			{
    			$data[] = $row;
			}
			header('Content-Type: application/json');
			echo json_encode($data,JSON_PRETTY_PRINT);
		break;
		case 1:
			if(isset($_GET["classroom"])&&isset($_GET["commentcontent"]))
			{
				$classroom = intval($_GET["classroom"]);
				$commentcontent = strval($_GET["commentcontent"]);
				$sql = "insert into comments (classroom,commentcontent) values ('".$classroom."','".$commentcontent."')";
				$retval = mysqli_query($conn , $sql);
				if (!$retval) 
				{
					die('Query couldnt be executed'.mysqli_connect_error());
				}
			}
			break;
	}
}
mysqli_close($conn);
?>
