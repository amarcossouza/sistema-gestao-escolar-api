-- Script para criar usuário de teste no banco de dados

INSERT INTO esc_usuario (email, senha, ativo, criado_em, atualizado_em, ultimo_login, tentativas_login, senha_temporaria) 
VALUES (
    'admin@escola.com',
    '12345',
    true,
    NOW(),
    NOW(),
    NULL,
    0,
    false
);

-- Se quiser adicionar mais usuários:
INSERT INTO esc_usuario (email, senha, ativo, criado_em, atualizado_em, ultimo_login, tentativas_login, senha_temporaria) 
VALUES (
    'professor@escola.com',
    'senha123',
    true,
    NOW(),
    NOW(),
    NULL,
    0,
    false
);

INSERT INTO esc_usuario (email, senha, ativo, criado_em, atualizado_em, ultimo_login, tentativas_login, senha_temporaria) 
VALUES (
    'aluno@escola.com',
    'aluno123',
    true,
    NOW(),
    NOW(),
    NULL,
    0,
    false
);

-- Para verificar os usuários criados:
-- SELECT * FROM esc_usuario;
