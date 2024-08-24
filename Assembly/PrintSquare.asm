.data
	output: .asciiz "Informe o tamanho:"
	ast: .asciiz "*"
	qLine: .asciiz "\n"
.text
	#Enviando esses dados para a saída padrão d usuário
	li $v0, 4
	la $a0, output
	syscall
	
	#Leitura de dados do usuário
	li $v0, 5
	syscall
	
	#Tamanho do quadrado
	move $t0, $v0
	
	#Iniciando a impressão das linhas
	li $t1, 0
	ble $t0, $zero, saida
	
line:
	li $t2, 0
column:
	
	li $v0, 4
	la $a0, ast
	syscall
	addi $t2, $t2, 1
	blt $t2, $t0, column
	
	li $t2, 0
	li $v0, 4
	la $a0, qLine
	syscall
	
	addi $t1, $t1, 1
	blt $t1, $t0, line

saida:
	
	
		
