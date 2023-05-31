import java.util.Random;

class No {
    int dado;
    No anterior;
    No proximo;

    public No(int dado) {
        this.dado = dado;
        this.anterior = null;
        this.proximo = null;
    }
}

class ListaDuplamenteEncadeada {
    private No inicio;
    private No fim;

    public ListaDuplamenteEncadeada() {
        this.inicio = null;
        this.fim = null;
    }

    public void inserirEmOrdem(int dado) {
        No novoNo = new No(dado);

        if (inicio == null) {
            inicio = novoNo;
            fim = novoNo;
        } else if (dado <= inicio.dado) {
            novoNo.proximo = inicio;
            inicio.anterior = novoNo;
            inicio = novoNo;
        } else if (dado >= fim.dado) {
            novoNo.anterior = fim;
            fim.proximo = novoNo;
            fim = novoNo;
        } else {
            No atual = inicio;
            while (atual != null && atual.dado < dado) {
                atual = atual.proximo;
            }
            novoNo.proximo = atual;
            novoNo.anterior = atual.anterior;
            atual.anterior.proximo = novoNo;
            atual.anterior = novoNo;
        }
    }

    public void imprimirCrescente() {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.dado + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }

    public void imprimirDecrescente() {
        No atual = fim;
        while (atual != null) {
            System.out.print(atual.dado + " ");
            atual = atual.anterior;
        }
        System.out.println();
    }

    public void removerPrimos() {
        No atual = inicio;
        while (atual != null) {
            if (ehPrimo(atual.dado)) {
                if (atual == inicio) {
                    inicio = atual.proximo;
                    if (inicio != null) {
                        inicio.anterior = null;
                    }
                    atual = inicio;
                } else if (atual == fim) {
                    fim = atual.anterior;
                    fim.proximo = null;
                    atual = null;
                } else {
                    atual.anterior.proximo = atual.proximo;
                    atual.proximo.anterior = atual.anterior;
                    No temp = atual;
                    atual = atual.proximo;
                    temp.proximo = null;
                    temp.anterior = null;
                }
            } else {
                atual = atual.proximo;
            }
        }
    }

    private boolean ehPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
}

public class Lista {
    public static void main(String[] args) {
        int[] numeros = gerarNumerosAleatorios(1000, -9999, 9999);
        ListaDuplamenteEncadeada lista = new ListaDuplamenteEncadeada();

        for (int numero : numeros) {
            lista.inserirEmOrdem(numero);
        }

        System.out.println("Lista em ordem crescente:");
        lista.imprimirCrescente();

        System.out.println("Lista em ordem decrescente:");
        lista.imprimirDecrescente();

        lista.removerPrimos();

        System.out.println("Lista após remoção de números primos:");
        lista.imprimirCrescente();
    }

    private static int[] gerarNumerosAleatorios(int quantidade, int minimo, int maximo) {
        int[] numeros = new int[quantidade];
        Random random = new Random();
        for (int i = 0; i < quantidade; i++) {
            numeros[i] = random.nextInt((maximo - minimo) + 1) + minimo;
        }
        return numeros;
    }
}
