<x-layout>
    <div class="flexbox">
        <div class="colspan-2">
            <h2 style="text-align:center;">Login</h2>
            @if($_POST != null && $status == "login")
            <p style="text-align: center;">There was an error: {{ $error }}</p>
            <x-loginform :username="$username" :password="$password"></x-loginform>
            @else
            <x-loginform></x-loginform>
            @endif
        </div>
        <div class="colspan-2">
            <h2 style="text-align:center;">Register</h2>
            @if($_POST != null && $status == "register")
            <p style="text-align: center;">There was an error: {{ $error }}</p>
            <x-registerform :email="$_POST['email']" :username="$_POST['username']" :password="$_POST['password']"></x-registerform>
            @else
            <x-registerform></x-registerform>
            @endif
        </div>
    </div>
</x-layout>