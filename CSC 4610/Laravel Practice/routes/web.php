<?php

use App\Models\Cat;
use Illuminate\Http\Request;
use App\Http\Controllers\LoginController;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\DB;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('index');
});

Route::get('/form', function () {
    return view('form', [$_POST, 'e' => null]);
});

Route::get('/formsubmit', function(){
    return view('formsubmit');
});

Route::get('/register', function(){
    return view('register');
});

Route::get('/cat{cat}', function (Cat $cat) {
    return view('cat', ['cat' => $cat]);
});

Route::get('/logout', function(Request $request){
    return LoginController::logout($request);
});

Route::post('/formsubmit{type}', function($type){
    if($type == 1){
        try{
            DB::table('cats')->insert(
                ['name' => $_POST['name'],
                'age' => intval($_POST['age']),
                'breed' => $_POST['breed']
            ]);
        }
        catch(Exception $e){
            return view('form', [$_POST, 'e' => $e]);
        }  
    }
    else if($type == 2){
        try{
            DB::table('users')->insert(
                ['email' => $_POST['email'],
                'username' => $_POST['username'],
                'password' => bcrypt($_POST['password'])
            ]);
        }
        catch(Exception $e){
            return view('register', [$_POST, 'error' => $e->getMessage(), 'status' => "register"]);
        }
    }
    $_POST = [];
    return redirect('/formsubmit');
});

Route::post('/register', function(Request $request){
    try{
        DB::table('users')->insert(
            ['email' => $_POST['email'],
            'username' => $_POST['username'],
            'password' => bcrypt($_POST['password'])
        ]);
    }
    catch(Exception $e){
        return view('register', [$_POST, 'error' => $e->getMessage(), 'status' => 'register']);
    }
    return LoginController::authenticate($request);
});

Route::post('/login', function(Request $request){
    return LoginController::authenticate($request);
});