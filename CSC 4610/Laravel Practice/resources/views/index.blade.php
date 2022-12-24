<?php
    use App\Models\Cat;
    use Illuminate\Support\Facades\Auth;
?>

<x-layout>
    <h4 class="center grey-text">Cats</h4>

    <div class="container">
        @if(Auth::check())
            <h6 class="brand-text">Welcome to the site, {{ auth()->user()->username }}</h6>
        @else
            <h6 class="brand-text">Welcome to the site</h6>
        @endif
        <div class="row">
            @foreach(Cat::all() as $cat)
                <div class="col s6 m4">
                    <div class="card z-depth-0">
                        <div class="card-content center">
                            <h6>{{ $cat->name }}</h6>
                            <ul class="grey-text">
                                <li>Age: {{ $cat->age }}</li>
                                <li>Breed: {{ $cat->breed }}</li>
                            </ul>
                        </div>
                        <div class="card-action right-align">
                            <a class="brand-text" href="/cat{{$cat->id}}">more info</a>
                        </div>
                    </div>
                </div>
            @endforeach
        </div>
    </div>
</x-layout>