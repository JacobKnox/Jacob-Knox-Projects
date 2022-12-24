@props([
    'email' => null,
    'username' => null,
    'password' => null
])

<form action="/register" method="POST">
    @csrf
    <label for="email">Email: </label>
    <input name="email" type="email" required placeholder="john.doe@gmail.com" value="{{ $email }}">
    <label for="username">Username: </label>
    <input name="username" type="text" required placeholder="JohnDoe2020" value="{{ $username }}">
    <label for="password">Password: </label>
    <input name="password" type="password" required value="{{ $password }}">
    <input type="submit" value="Register">
</form>