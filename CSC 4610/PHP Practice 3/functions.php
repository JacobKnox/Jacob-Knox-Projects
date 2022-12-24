<?php
    class Cat{
        public $id;
        public $name;
        public $age;
        public $color;
    }

    class Database{
        protected $host;
        protected $name;
        protected $username;
        protected $password;

        public function __construct($host, $dbname, $username, $password){
            $this->host = $host;
            $this->name = $dbname;
            $this->username = $username;
            $this->password = $password;
        }

        public function connect(){
            try{
                $pdo = new PDO("mysql:host=".$this->host.";dbname=".$this->name, $this->username, $this->password);
            }
            catch(PDOException $e){
                throw new Exception("PDOException occurred with message: ".$e->getMessage());
            }
            return $pdo;
        }

        public function query($table, $condition, $class){
            try{
                if((bool) $condition){
                    $sql = $this->connect()->prepare("select * from {$table} where {$condition}");
                }
                else{
                    $sql = $this->connect()->prepare("select * from {$table}");
                }
            }
            catch(Exception $e){
                throw new Exception($e->getMessage());
            }
            if($sql->execute()){
                return $sql->fetchAll(PDO::FETCH_CLASS, $class);
            }
            else{
                throw new Exception("Query execution failed.");
            }
        }

        public function insert($table, $params, $data){
            try{
                $sql = $this->connect()->prepare("insert into cats (".implode(", ", $params).") values (:".implode(", :", $params).")");
            }
            catch(Exception $e){
                throw new Exception($e->getMessage());
            }
            if($sql->execute($data)){
                return true;
            }
            else{
                throw new Exception("Insert execution failed.");
            }
        }

        public function delete($table, $condition){
            try{
                if((bool) $condition){
                    $sql = $this->connect()->prepare("delete from {$table} where {$condition}");
                }
                else{
                    $sql = $this->connect()->prepare("delete from {$table}");
                }
            }
            catch(Exception $e){
                throw new Exception($e->getMessage());
            }
            if($sql->execute()){
                return true;
            }
            else{
                throw new Exception("Delete execution failed.");
            }
        }
    }

    return new Database("localhost:8889", "cats", "root", "root");
?>