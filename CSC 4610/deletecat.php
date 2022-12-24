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
            try{
                if($myDB->delete("cats", "id=".$_GET['id'])){
                    echo "<h1 id='success'>The delete was successful!</h1>";
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