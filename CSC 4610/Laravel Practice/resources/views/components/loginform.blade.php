@props([
    'username' => null,
    'password' => null
])

<form action="/login" method="POST">
    @csrf
    <label for="username">Username: </label>
    <input name="username" type="text" required placeholder="JohnDoe2020" value="{{ $username }}">
    <label for="password">Password: </label>
    <input name="password" type="password" required value="{{ $password }}">
    <input type="submit" value="Login">
</form>