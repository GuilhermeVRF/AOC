.data
	amount_message: .asciiz "Informe o total do pedido: "
	note_message: .asciiz "Nota : R$"
	semDinheiro_message: .asciiz "Sem fundos suficientes!"
	br: .asciiz "\n"
	notes: .word 10, 10, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2
	
.text
	# Carregando a base do array em um registrador
	la $t0, notes
	
	# Imprimindo a mensagem solicitando o valor totalda compra
	li $v0, 4
	la $a0, amount_message
	syscall
	
	# Solicitando a entrada do valor total da compra	
	li $v0, 5
	syscall
	
	bgt $v0, 90, semDinheiro 
	# Armazenando o total da compra no registrador $t2
	move $t2, $v0
	
	# Criando um contador para saber a nota atual $t3
	li $t3, 0
	
	j verificarNotas
	
verificarNotas:
	# Subtraindo o amount - nota atual
	lw $t5, ($t0)
	sub $t4, $t2, $t5
	
	#Se for maior que 0
	bge $t4,-1, atualizarAmount
	blt $t4, $zero, notaInvalida
	
	atualizarAmount:
		# Nota
		li $v0, 4
		la $a0, note_message
		syscall
	
		# Imprime a nota
		li $v0, 1
		move $a0, $t5
		syscall 
		
		# \n
		li $v0, 4
		la $a0, br
		syscall 
		
		# Atualiza o valor da nota
		sub $t2, $t2, $t5
		
	notaInvalida:
	
	addi $t0, $t0, 4
	addi $t3, $t3, 4

	blt $t3, 88, verificarNotas
	j fim
	
	semDinheiro:
	
		li $v0, 4
		la $a0, semDinheiro_message
		syscall
	fim:
