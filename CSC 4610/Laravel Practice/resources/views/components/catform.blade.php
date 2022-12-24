@props([
    'name' => null,
    'age' => null,
    'breed' => null
])

<form action="/formsubmit1" method="POST">
@csrf
    <label for="name">Name: </label>
    <input type="text" name="name" required value="{{ $name }}">
    <label for="age">Age: </label>
    <input type="number" name="age" min="0" required value="{{ $age }}">
    <label for="breed">Breed: </label>
    <input type="text" name="breed" required value="{{ $breed }}">
    <input type="submit">
</form>