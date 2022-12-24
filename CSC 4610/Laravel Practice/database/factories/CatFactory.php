<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Cat>
 */
class CatFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        $breeds = array('Abyssinian', 'American Bobtail', 'American Curl', 'American Shorthair', 'American Wirehair', 'Balinese-Javanese', 'Bengal');
        $max = count($breeds) - 1;
        return [
            'name' => fake()->name(),
            'age' => rand(1, 40),
            'breed' => $breeds[rand(0, $max)]
        ];
    }
}
