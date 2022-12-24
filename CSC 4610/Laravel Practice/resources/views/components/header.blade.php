<?php
    use Illuminate\Support\Facades\Auth;
?>

<nav class="white z-depth-0">
    <div class="container">
        <img id="logo" src="\images\cat-icon.png">
        <a href="/" class="brand-logo brand-text">Cat Database</a>
        <ul id="nav-mobile" class="right hide-on-small-and-down">
            @if(Auth::check())
                <li class="grey-text">Hello {{ auth()->user()->username }}</li>
                <li><a href="/logout" class="btn brand z-depth-0">Log Out</a></li>
            @else
                <li><a href="/register" class="btn brand z-depth-0">Login/Register</a></li>
            @endif
            <li><a href="/form" class="btn brand z-depth-0">Add a Cat</a></li>        
        </ul>
    </div>
</nav>