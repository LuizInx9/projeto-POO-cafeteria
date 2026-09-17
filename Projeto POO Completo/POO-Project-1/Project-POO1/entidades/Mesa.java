package entidades;


import entidades.enums.StatusMesa;

public class Mesa {

        private int numMesa;
        private int capacidadeMesa;
        private StatusMesa statusMesa;

        // Construtor
        public Mesa(int numMesa, int capacidadeMesa, StatusMesa statusMesa) {
            this.numMesa = numMesa;
            this.capacidadeMesa = capacidadeMesa;
            this.statusMesa = statusMesa;
        }

        // Getters
        public int getNumMesa() {
            return numMesa;
        }

        public int getCapacidadeMesa() {
            return capacidadeMesa;
        }

        public StatusMesa getStatusMesa() {
            return statusMesa;
        }

        // Setters
        public void setNumMesa(int numMesa) {
            this.numMesa = numMesa;
        }

        public void setCapacidadeMesa(int capacidadeMesa) {
            this.capacidadeMesa = capacidadeMesa;
        }

        public void setStatusMesa(StatusMesa statusMesa) {
            this.statusMesa = statusMesa;
        }

        // toString
        @Override
        public String toString() {
            return "Mesa " + numMesa + " | Capacidade: " + capacidadeMesa + " pessoas | Status: "
                    + statusMesa;
        }
    }

