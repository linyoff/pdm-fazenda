package com.trabalhofazendinha

class Fazenda (var registro : String, var nome : String, var valor : Float, var latitude : Float, var longitude : Float ) {

    override fun toString(): String {
        return "Fazenda: $nome\n" +
                "Registro: $registro\n" +
                "Valor: $valor\n" +
                "Latitude: $latitude\n" +
                "Longitude: $longitude\n"
    }
}