<?php
    // A class to store all of the information from the database for each cat
    class Cat{
        public $id;
        public $name;
        public $age;
        public $color;
    }

    // A general purpose function to fetch all information from a specified database table and return it
    function prepDB($host, $dbname, $table, $username, $password, $saveClass){
        try{
            $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
        }
        catch(PDOException $e){
            echo $e;
            die();
        }
        $statement = $pdo->prepare("select * from $table");
        $statement->execute();
        $dbResults = $statement->fetchAll(PDO::FETCH_CLASS, $saveClass);
        return $dbResults;
    }

    $results = prepDB("localhost:8889", "cats", "cats", "root", "root", "Cat");
    // Gets the id field in the url (if there is one) and cleans it to prevent malicious code
    $idInput = htmlspecialchars($_GET['id']);
?>

<!DOCTYPE html>
<html>
    <head>
        <style>
            *{
                text-align: center;
            }
            #homebutton{
                width: 100%;
                height: 100px;
                font-size: 48px;
            }
            button{
                background-color: black;
                color: white;
                cursor: pointer;
            }
            button:hover{
                background-color: white;
                border: 1px black solid;
                color: black;
            }
            #flex{
                display: flex;
            }
            #flex > div{
                width: 25%;
                border: 2px black solid;
                margin: 5px 5px 5px 5px;
                padding: 5px 5px 5px 5px;
            }
            .catbutton{
                margin-bottom: 20px;
                border-radius: 8px;
            }
        </style>
    </head>
    <body>
        <a id="home" href="http://localhost:8888/phppractice2.php"><button id="homebutton">Home</button></a>
        <?php if($idInput != null): ?>
            <?php if($idInput < 1 || $idInput > count($results)): ?>
                <p>No cat with that id exists.</p>
            <?php else: ?>
                <?php $thisCat = $results[intval($idInput) - 1]; ?>
                <h1><?= $thisCat->name ?></h1>
                <p>Age: <?= $thisCat->age ?></p>
                <p>Color: <?= $thisCat->color ?></p>
            <?php endif; ?>
        <?php else: ?>
            <h1>Cat Database</h1>
            <div id='flex'>
                <?php foreach($results as $cat): ?>
                    <div>
                        <h2><strong><?= $cat->name ?></strong></h2>
                        <p><?= $cat->color ?></p>
                        <?= "<a href='http://localhost:8888/phppractice2.php/?id=$cat->id'>" ?><button class='catbutton'>Details</button></a>
                    </div>
                <?php endforeach; ?>
            </div>
        <?php endif; ?>
    </body>
</html>