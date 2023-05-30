package com.shopccer.common.entity;

public enum EstadoPedido {

    NUEVO {
        @Override
        public String defaultDescription() {
            return "El pedido fue realizado por el cliente";
        }

    },

    CANCELADO {
        @Override
        public String defaultDescription() {
            return "El pedido fue cancelado";
        }
    },

    EN_PROCESO {
        @Override
        public String defaultDescription() {
            return "El pedido está siendo procesado";
        }
    },

    EMPAQUETADO {
        @Override
        public String defaultDescription() {
            return "Los productos han sido empaquetados";
        }
    },

    RECOGIDO {
        @Override
        public String defaultDescription() {
            return "La empresa de transporte ha recogido el paquete";
        }
    },

    EN_TRANSITO {
        @Override
        public String defaultDescription() {
            return "La empresa de transporte está en camino";
        }
    },

    ENTREGADO {
        @Override
        public String defaultDescription() {
            return "El cliente ha recibido los productos";
        }
    },

    DEVUELTO {
        @Override
        public String defaultDescription() {
            return "Los productos han sido devueltos";
        }
    },

    PAGADO {
        @Override
        public String defaultDescription() {
            return "El cliente ha pagado el pedido";
        }
    },

    REEMBOLSADO {
        @Override
        public String defaultDescription() {
            return "Se ha reembolsado el importe de los productos al cliente";
        }
    };

    public abstract String defaultDescription();
}
