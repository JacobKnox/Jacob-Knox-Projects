<?php
    $_POST['senior'] = false;

    $myDB = require("functions.php");

    try{
        $results = $myDB->query("cats", "", "Cat");
    }
    catch(Exception $e){
        echo $e->getMessage();
        die();
    }

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
            }
            button:hover{
                background-color: white;
                border: 1px black solid;
                color: black;
                cursor: pointer;
            }
            #flex{
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                margin-top: 20px;
                margin-bottom: 20px;
            }
            #flex > div{
                min-width: 20%;
                border: 2px black solid;
                margin: 5px 5px 5px 5px;
                padding: 5px 5px 5px 5px;
            }
            .catbutton{
                margin-bottom: 20px;
                border-radius: 8px;
            }
            #formButtons{
                margin-top: 20px;
            }
            .formButton{
                border-radius: 8px;
                color: white;
            }
            .formButton:hover{
                cursor: pointer;
            }
            #submit{
                background-color: green;
                width: 125px;
            }
            #reset{
                background-color: red;
                width: 125px;
            }
        </style>
    </head>
    <body>
        <a id="home" href="http://localhost:8888/phppractice3.php/?senior=<?= intval($_GET['senior']) ?>"><button id="homebutton">Home</button></a>
        <?php if($idInput != null): ?>
            <?php
                $catExists = false;
                foreach($results as $cat){
                    if($cat->id == intval($_GET['id'])){
                        $catExists = true;
                        $queriedCat = $cat;
                    }
                }
            ?>
            <?php if(!$catExists): ?>
                <p>No cat with that id exists.</p>
            <?php else: ?>
                <h1><?= $queriedCat->name ?></h1>
                <p>Age: <?= $queriedCat->age ?></p>
                <p>Color: <?= $queriedCat->color ?></p>
                <form method="post" action="http://localhost:8888/deletecat.php/?id=<?= $queriedCat->id ?>">
                    <input type="submit" value="Delete">
                </form>
            <?php endif; ?>
        <?php else: ?>
            <h1>Cat Database</h1>
            <a href="http://localhost:8888/phppractice3.php/?senior=<?= intval(!$_GET['senior']) ?>"><button>Toggle Seniors</button></a>
            <div id='flex'>
                <?php if((bool) $_GET['senior']): ?>
                    <?php foreach($results as $cat): ?>
                        <?php if($cat->age > 10): ?>
                            <div>
                                <h2><strong><?= $cat->name ?></strong></h2>
                                <p><?= $cat->color ?></p>
                                <?= "<a href='http://localhost:8888/phppractice3.php/?id=$cat->id&senior=".$_GET['senior']."'>" ?><button class='catbutton'>Details</button></a>
                            </div>
                        <? endif; ?>
                    <?php endforeach; ?>
                <?php else: ?>
                    <?php foreach($results as $cat): ?>
                        <div>
                            <h2><strong><?= $cat->name ?></strong></h2>
                            <p><?= $cat->color ?></p>
                            <?= "<a href='http://localhost:8888/phppractice3.php/?id=$cat->id'>" ?><button class='catbutton'>Details</button></a>
                        </div>
                    <?php endforeach; ?>
                <?php endif; ?>
            </div>
            <h1>Add a Cat</h1>
            <form method="post" action="http://localhost:8888/insertcat.php">
                <p>Name</p><input name="name" type="text" required>
                <p>Age</p><input name="age" type="number" required>
                <p>Color</p><input name="color" type="text" required>
                <div id="formButtons">
                    <input class="formButton" id="submit" type="submit">
                    <input class="formButton" id="reset" type="reset">
                </div>          
            </form>
        <?php endif; ?>
    </body>
</html>