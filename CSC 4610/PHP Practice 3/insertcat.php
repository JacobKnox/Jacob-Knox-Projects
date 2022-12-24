<!DOCTYPE html>
<html>
<head>
    <style>
        *{
            text-align: center;
        }
    </style>
</head>
<body>
    <?php
    $myDB = require("functions.php");

    $data = [
        'name' => $_POST["name"],
        'age' => intval($_POST["age"]),
        'color' => $_POST["color"],
    ];

    try{
        if($myDB->insert("cats", ["name", "age", "color"], $data)){
            echo "<h1 id='success'>The insert was successful!</h1>";
            echo "<p id='submessage'>You will be redirected to the home page shortly. . .</p>";
            header("refresh:5;url=http://localhost:8888/phppractice3.php");
        }
    }
    catch(Exception $e){
        echo $e->getMessage();
        die();
    }
    
    $_POST = array();
?>
</body>


</html>