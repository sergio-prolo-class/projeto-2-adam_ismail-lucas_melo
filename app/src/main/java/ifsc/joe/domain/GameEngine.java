package ifsc.joe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.ui.FiltroTipo;

public class GameEngine {

    private final List<Personagem> personagens;
    private int nextId = 1;
    private final Random random;

    private int mortesAldeao = 0;
    private int mortesArqueiro = 0;
    private int mortesCavaleiro = 0;

    public GameEngine() {
        this.personagens = new ArrayList<>();
        this.random = new Random();
    }

    public void adicionarPersonagem(Personagem p) {
        p.setId(nextId++);
        personagens.add(p);
    }

    public int getNextId() {
        return nextId;
    }

    public List<Personagem> getPersonagens() {
        return personagens;
    }

    // ----------------------------------------
    //  LOOP DE ATUALIZAÇÃO
    // ----------------------------------------
    public void atualizarTodos() {

        for (Personagem p : personagens) {
            p.atualizar();
        }

        removerFinalizados();
    }

    // ----------------------------------------
    //  REMOVE APENAS APÓS FADE
    // ----------------------------------------
    private void removerFinalizados() {

        personagens.removeIf(p -> {

            if (!p.isVivo() && p.getAlpha() <= 0) {

                if (p instanceof Aldeao) mortesAldeao++;
                if (p instanceof Arqueiro) mortesArqueiro++;
                if (p instanceof Cavaleiro) mortesCavaleiro++;

                System.out.println("Removido após fade: " + p.getClass().getSimpleName());
                return true;
            }

            return false;
        });
    }

    // ----------------------------------------
    //  GERAÇÃO ALEATÓRIA
    // ----------------------------------------
    public Personagem gerarAleatorio(int x, int y) {

        int escolha = random.nextInt(3);
        Personagem p;

        switch (escolha) {
            case 0 -> p = new Aldeao(x, y, nextId);
            case 1 -> p = new Arqueiro(x, y, nextId);
            case 2 -> p = new Cavaleiro(x, y, nextId);
            default -> throw new IllegalStateException("Erro ao gerar personagem");
        }

        adicionarPersonagem(p);
        return p;
    }

    // ----------------------------------------
    //  ATAQUE GLOBAL
    // ----------------------------------------
    public void atacarGuerreiros(int distancia) {

        for (Personagem atacante : personagens) {

            if (!(atacante instanceof ifsc.joe.domain.interfaces.Lutador)) continue;

            atacante.setAtacando(true);

            for (Personagem alvo : personagens) {

                if (alvo == atacante) continue;
                if (!alvo.isVivo()) continue;

                double dx = atacante.getX() - alvo.getX();
                double dy = atacante.getY() - alvo.getY();
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist <= distancia) {
                    alvo.receberDano(atacante.getAtaque());
                }
            }
        }
    }

    // ----------------------------------------
    //  ATAQUE COM FILTRO
    // ----------------------------------------
    public void atacarFiltrados(FiltroTipo filtro) {

        int distancia = Config.getInt("engine.colisao.distancia");

        for (Personagem atacante : personagens) {

            if (!aplicaFiltro(atacante, filtro)) continue;
            if (!(atacante instanceof ifsc.joe.domain.interfaces.Lutador)) continue;

            atacante.setAtacando(true);

            for (Personagem alvo : personagens) {

                if (alvo == atacante) continue;
                if (!alvo.isVivo()) continue;

                double dx = atacante.getX() - alvo.getX();
                double dy = atacante.getY() - alvo.getY();
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist <= distancia) {
                    alvo.receberDano(atacante.getAtaque());
                }
            }
        }
    }

    private boolean aplicaFiltro(Personagem p, FiltroTipo filtro) {
        return switch (filtro) {
            case TODOS -> true;
            case ALDEAO -> p instanceof Aldeao;
            case ARQUEIRO -> p instanceof Arqueiro;
            case CAVALEIRO -> p instanceof Cavaleiro;
        };
    }
}
