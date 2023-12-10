package com.proyectoFinal.homebanking.tools;

import java.util.Arrays;
import java.util.List;

public class Utility {


    /**
     * @author Eduardo
     */
    public static String generadorCbu(){
        int i=1;
        String cadena="";
        while (i<24){
            int randomNum = (int)(Math.random() * 10);
            //cbu.add(randomNum);
            cadena += String.valueOf(randomNum);
            //String cbuCadena=cadena;
            i++;
            //System.out.print(cbuCadena);
        }
        return cadena;
    }

    /**
     * @author Florencia
     */
    public static String generateAlias(){
        return (generateRandomWords() + "." + generateRandomWords() + "." + generateRandomWords()).toUpperCase();
    }

    /**
     * @author Florencia
     */
    public static String generateRandomWords(){

        List<String> words = Arrays.asList(
                "acero", "agradable", "ala", "alambre", "alba", "alga", "alho", "almeja", "alondra", "alpiste",
                "altar", "amable", "amago", "ameno", "amor", "amparo", "ancla", "ansia", "arroz", "apaño",
                "apio", "araña", "arar", "arco", "arduo", "aroma", "asado", "alma", "aspa", "asta", "astro",
                "atun", "aval", "ave", "avion", "azul", "bache", "bálsamo", "banca", "bar", "bata", "bazar",
                "bello", "bicho", "bisonte", "blanco", "bravo", "brote", "brujo", "buey", "burro", "cabra",
                "cacao", "cada", "cafe", "caida", "caja", "calar", "caldo", "callo", "calma", "calvo", "calzado",
                "calzar", "camion", "cana", "canto", "caña", "capa", "capar", "carbo", "carbono", "casa", "casar",
                "casco", "casero", "catorce", "cazador", "cazo", "cebra", "cebo", "ceder", "cedro", "cedo",
                "cegar", "celda", "celo", "cepa", "cerco", "cereza", "cerio", "cero", "cerveza", "cesar",
                "cetro", "chal", "chapa", "charco", "chico", "chino", "chispa", "choza", "cian", "cigarro",
                "ciento", "cifra", "cima", "cine", "circo", "cito", "clavo", "claro", "cloro", "club", "cobre",
                "coco", "col", "coles", "colador", "collar", "colectivo", "colega", "colina", "collado", "color",
                "coloso", "comba", "combro", "comer", "como", "compas", "compota", "caracol", "corcho", "conejo",
                "cono", "conosco", "contar", "coñac", "copa", "comer", "corda", "coro", "corra", "corte", "corto",
                "corza", "cota", "cozar", "crear", "creer", "crema", "crimen", "crio", "crisis", "cromo", "cuadro",
                "cuarzo", "cuarto", "cuba", "cubo", "cucar", "cuchu", "cuello", "cuerpo", "cuerpo", "cuesta", "cuido",
                "cuñado", "cuna", "cundir", "cuneo", "cuota", "cura", "curar", "curva", "curvo", "cuyo", "curcuma",
                "cúscús", "dacron", "dado", "dalton", "dar", "dato", "debo", "débil", "dedal", "dedo",
                "defumado", "dejo", "delantal", "delgado", "delirio", "delta", "denso", "deparo", "depo",
                "deporte", "depre", "derecha", "derrota", "despojo", "desteñir", "deta",
                "detras", "devorar", "devo", "dia", "diablo", "dado", "dado", "dido", "diente", "digito", "diluir",
                "dínamo", "diosa", "díos", "dire", "dito", "doble", "dodo", "dogma", "dolce", "doña", "don",
                "donde", "dorar", "dosa", "dote", "doyen", "droga", "duro", "duvet", "facil", "facha", "facho",
                "fallo", "falto", "fama", "faro", "fascio", "faso", "fértil", "ferza", "feudo", "fiar", "fideo",
                "fiera", "fijar", "fin", "fino", "finta", "firma", "fisco", "fisga", "fíu", "flamenco", "fobia",
                "foso", "foto", "frasco", "frío", "frio", "frito", "frodo", "fruta", "fulcro", "fulgor", "futil", "futo", "futuro", "galgo", "galio", "gallo", "galón", "galvano", "gama", "gamba", "gana",
                "gangster", "garbo", "garzo", "gazapo", "gineo", "ginete", "gira", "gitano", "global", "globo",
                "goce", "barco", "guitarra", "playa", "tigre", "ventana", "caramelo", "elefante", "reloj", "jirafa", "robot",
                "viento", "rio", "fuego", "avion", "montaña", "delfín", "flor", "espada", "espejo", "helado",
                "libro", "luz", "arcoiris", "dragón", "isla", "mariposa", "oso", "cielo", "rayo", "silla", "sombra", "nieve", "unicornio", "vuelo", "agua", "trueno", "selva", "fruta", "caracol",
                "nube", "camino", "campana", "circulo", "serpiente", "burbuja", "globo", "gato", "perla", "hoja",
                "estrella", "lapiz", "pepino", "león", "caracol", "oso", "piano", "hoja", "rayo", "pajaro",
                "azucar", "rana", "sueño", "coco", "pluma", "mar", "salto", "piedra", "hoja", "nido",
                "puente", "puzzle", "sombrero", "radio", "hongo", "hoja", "guitarra", "laberinto", "llama", "espejo",
                "ciudad", "pintura", "cuerda", "ventilador", "girasol", "hoja", "risa", "palabra", "barba", "cuchillo",
                "radio", "flauta", "flecha", "robot", "huevo", "tigre", "gota", "fruta", "tren", "platano",
                "escalera", "caramelo", "pájaro", "abeja", "bandera", "caballo", "hueso", "pastel", "pelota", "pintura",
                "piñata", "rayo", "telescopio", "tienda", "tijeras", "zapato", "roca", "arcoiris", "labio",
                "espiral", "lupa", "cangrejo", "faro", "fruta", "papel", "torre", "elefante", "globo", "unicornio",
                "fruta", "cabina", "espejo", "flecha", "nido", "peluca", "alfombra", "tomate", "elefante", "laser",
                "elefante", "guitarra", "hoja", "piano", "navaja", "radio", "puzzle", "pelota", "jarra", "búho",
                "anillo", "niebla", "candado", "espiral", "isla", "pantera", "guitarra", "hilo", "guitarra", "telescopio",
                "ancla", "pastel", "bandera", "silla", "girasol", "cuerda", "rosa", "burbuja", "pajaro", "radio",
                "puzzle", "cuerda", "papel", "piano", "burbuja", "flor", "piedra", "rayo", "guitarra", "isla",
                "candado", "rayo", "hilo", "guitarra", "rosa", "fruta", "guitarra", "piano", "faro", "jaula",
                "platano", "hoja", "roca", "unicornio", "laberinto", "robot", "telescopio", "puzzle", "pluma");

        return words.get((int)(Math.random() * words.size()) + 1);


    }


}
