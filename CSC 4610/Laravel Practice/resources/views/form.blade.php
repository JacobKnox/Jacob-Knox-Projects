<x-layout>
    @if($_POST != null)
        <p style="text-align: center;">There was an error: {{ $e->getMessage() }}.</p>
        <x-catform :name="$_POST['name']" :age="intval($_POST['age'])" :breed="$_POST['breed']"></x-catform>
    @else
        <x-catform></x-catform>
    @endif
</x-layout>