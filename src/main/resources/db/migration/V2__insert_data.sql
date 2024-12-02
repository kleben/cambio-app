-- Inserções iniciais para a tabela `moeda`
INSERT INTO moeda (nome) VALUES
                             ('Ouro Real'),
                             ('Tibar');

-- Inserções iniciais para a tabela `produto`
INSERT INTO produto (nome, valor) VALUES
                                      ('Peles', 50.00),
                                      ('Madeira', 30.00),
                                      ('Hidromel', 20.00);

-- Inserções iniciais para a tabela `reino`
INSERT INTO reino (nome) VALUES
                             ('Wefin'),
                             ('Montanhas Distantes'),
                             ('Vale Verdejante');

-- Inserindo taxas de câmbio para as moedas existentes
INSERT INTO public.taxa_cambio (id, moeda_id, taxa, data_hora) VALUES (1, 1, 1.0000, '2024-11-30 00:00:00.000000');
INSERT INTO public.taxa_cambio (id, moeda_id, taxa, data_hora) VALUES (2, 2, 2.5000, '2024-11-30 00:00:00.000000');
INSERT INTO public.taxa_cambio (id, moeda_id, taxa, data_hora) VALUES (3, 2, 3.0000, '2024-12-01 23:53:16.603594');


INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (1, 2, 1, 1, 1, null, 63.00, 3.0000, null, '2024-12-01 21:39:53.508223', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (2, 2, 2, 2, 1, null, 25.20, 3.0000, null, '2024-12-01 21:41:51.190812', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (3, 2, 2, 2, 1, null, 25.20, 3.0000, null, '2024-12-01 21:42:22.187984', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (4, 2, 2, 2, 1, null, 25.20, 3.0000, null, '2024-12-01 21:44:29.862475', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (5, 2, 2, 2, 1, null, 157.50, 3.0000, null, '2024-12-01 21:46:30.725913', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (6, 3, 2, 2, 1, null, 100.00, 0.0000, null, '2024-12-01 21:47:51.684675', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (7, 3, 2, 2, 1, null, 100.00, 0.0000, 2.0000, '2024-12-01 22:58:23.772414', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (8, 3, 2, 1, 1, null, 100.00, 0.0000, 2.0000, '2024-12-01 23:01:40.777928', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (9, null, 1, 2, 1, 0.3300, 30.00, null, null, '2024-12-02 00:47:03.822913', 'TROCA_MOEDA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (10, 2, 1, 1, 2, null, 33.00, 3.0000, 1.0000, '2024-12-02 09:47:39.083390', 'VENDA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (11, 2, 1, 1, 2, null, 99.00, 9.0000, 3.0000, '2024-12-02 09:47:45.639838', 'VENDA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (12, 2, 2, 1, 2, null, 990.00, 30.0000, 10.0000, '2024-12-02 09:47:53.807066', 'VENDA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (13, 2, 1, 1, 1, null, 31.50, 1.5000, 1.0000, '2024-12-02 10:03:43.664284', 'COMPRA');
INSERT INTO public.transacao (id, produto_id, moeda_origem_id, moeda_destino_id, reino_id, taxa_cambio, valor_transacao, taxa_adicional, qtd_produtos, data_hora, tipo_transacao) VALUES (14, 2, 1, 1, 1, null, 31.50, 1.5000, 1.0000, '2024-12-02 10:04:28.419896', 'COMPRA');

