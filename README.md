# tiny
This is not YAML - bring back sanity to your mega YAML 

tINY is a superset of YAML


# Ideal Spec

## Comments

`#` char

## Basic type

### Environment variables

```
@env("var-name", default)

$var = @env("var-name", default)
```

### arrays

`[]`

### maps 

```
{key: "value has value" "bob age": 23}

{
  key: "value",
  another-key: "new value"
  "blah blah": "value",
  adas adsad ads : blash,
}

```

### Conditionals

```
$creds = @include(@env-var("ENV"))
```


#### if?

#### cond? or select?


### string

```
$string = "asdasd"

```

#### long strings

```
$long-string = |
               very long
               string
               with 
               stuff in it 

```
### Booleans

```
True
False
```

### numbers
```
1
1.12
-1
-1.123

```
### date?

### geo?

## Import

Imports functions and variables from another tINY file.

One can import modules by:

```
@import "some/path/lib"

```

## Include

Only accepts yaml docs, and puts them in place.

```
- people: @include("people.yaml")

$people = @include("people.yml")

@including("") # will yield a warning; do we want this?
```

## Variables

always start with `$` any character is accepted

## Functions

### Named functions

```
$name->person = @($name) => {
   - age: 123
     name: $name
}
```

### Anonymous functions

```
@($name) => {
   - age: 123
     name: $name
}

@($name, $age) => {
   - age: $age
     name: $name
}
```

## Built-ins

### for

See `map`

### map 

Allows you to iterate over stuff

``` 
# Referencing an existing function
- persons: @map($person-function, ["crusty", "clown"])

or 

# Defining an anonymous function inline
- persons: @map(($name) => {$person-function($name) }, ["crusty", "clown"])

- persons: @map(($name, $age) => {
   - age: $age
     name: $name
}, ["crusty", "clown"])


$bob = @(stuff) => {}

# Referencing an existing function
- persons: @map(person-function, ["crusty", "clown"])

or 

# Defining an anonymous function inline
- persons: @map(($name) => { person-function($name) }, ["crusty", "clown"])

```

### examples

```
$var1
$var-for
$var->asda
$is_cool?
```


## Example Use


```


@import "some/path/somelib"
@import "some/path/somelib.tiny"

@include("somo/path/yaml.yml")

$name->person = @(name) => { # convention
- name: $name
  age: 39
  address: "Whatever"
  job: "Programmer"
}

my_people:
  $name->person("Bob")
  $name->person("Max")


- job: adasdasdada
  name: bobob
  @include("job-body.yml")

my_people: @map(["bob", "cat"], ($name) => {
  - name: $name
  age: 39
  address: "Whatever"
  job: "Programmer"
})

```

# TODO:

- casting?
- conditionals? v2?
- array and hashmap operations? adding, popping, etc...
- arithmetic operations?
- iteration over arrays and hashmaps?
- spec verification of "valid concourse file", "valid cf manifest", "valid bosh manifest"
- in context error display... errors should show the context of their errors and how to attempt a fix.
- clojurescript or clojure?
- test pipeline
- live playground
- live repl
- website
- cli tool
- example projects ( standard repos with examples )
- semantic commits
