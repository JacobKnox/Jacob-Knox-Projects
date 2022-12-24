<x-layout>
    <h4 class="center grey-text">{{ $cat->name }}</h4>
    <div class="container">
        <p>{{ $cat->name }}'s ID is {{ $cat->id }}.</p>
        <p>{{ $cat->name }} is a {{ $cat->age }} year old {{ $cat->breed }}.</p>
    </div>
</x-layout>