<?php
function factorial($num){
    if($num == 1){
        return $num;
    }
    return $num * factorial($num - 1);
}

function calculate_bill($usage){
    if($usage > 250){
        return 1095 + ($usage - 250) * 6.5;
    }
    else if($usage > 150){
        return 575 + ($usage - 150) * 5.2;
    }
    else if($usage > 50){
        return 175 + ($usage - 50) * 4;
    }
    else{
        return $usage * 3.5;
    }
}
?>

<!DOCTYPE html>
<html>
<body>

<?php
$name = "Jacob";
echo "Welcome to the Party ".$name.".<br>";

for($i = 1; $i < 9; $i++){
    for($j = 0; $j < $i; $j++){
        echo "*";
    }
    echo "<br>";
}
?>

<table>
    <?php
    for($i = 0; $i < 8; $i++){
        echo "<tr>";
        for($j = 0; $j < 8; $j++){
            if($i % 2 == 0 && $j % 2 == 0){
                echo "<td style='width:50px;background-color:black;height:50px;'></td>";
            }
            else if($i % 2 == 1 && $j % 2 == 1){
                echo "<td style='width:50px;background-color:black;height:50px;'></td>";
            }
            else{
                echo "<td style='width:50px;background-color:white;height:50px;'></td>";
            }
        }
        echo "</tr>";
    }
    ?>
</table>

<h2>Factorials</h2>
<?php
echo factorial(5)."<br>";
echo factorial(10)."<br>";
echo factorial(20);
?>

<h2>Water Bill</h2>
<?php
echo "Your water bill is $".number_format(calculate_bill(htmlspecialchars($_GET['usage'])), 2, '.', '');
?>

</body>
</html>