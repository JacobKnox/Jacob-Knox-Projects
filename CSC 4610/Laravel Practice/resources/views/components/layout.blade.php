<?php
    use App\Models\Cat;
    use App\Models\User;
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Cat Database</title>
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
        <link rel="stylesheet" href="\css\main.css">
    </head>
    <body class="grey lighten-4">
        <x-header></x-header>

        {{ $slot }}
        
        <x-footer></x-footer>
    </body>
</html>