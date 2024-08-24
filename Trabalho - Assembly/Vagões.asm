.data:
	trem: .space 20
	msg_qtdeTrem: .asciiz "Informe quantos trens serão ordenados: "
	msg_qtdeVagoes: .asciiz "Informe a quantidade vagões do trem: "
	ligacao_vagoes: .asciiz "-"
	cabine_trem: .asciiz "[]>"
	quebra_linha:.asciiz "\n"
.text	
	# Exibe a mensagem ao usuário solicitando a quantidade casos de teste "Trens"
	li $v0, 4
	la $a0, msg_qtdeTrem
	syscall
	
	# Entrada de dados do usuário com a quantidade de casos de testes - $t0
	li $v0, 5
	syscall
	move $t0, $v0
	
	# Inicializa o contador de trens - $t8
	move $t8, $zero
	
	preencher_trem:
		# Exibe a mensagem ao usuário solicitando a quantidade de vagões
		li $v0, 4
		la $a0, msg_qtdeVagoes
		syscall	
		
		# Entrada de dados do usuário com a quantidade de vagões - $t2
		li $v0, 5
		syscall
		move $t2, $v0
	
		# Inicializa o contador de vagões em 0 - $t3
		move $t3, $zero
		
		# Carrega o primeiro vagão do trem a ser enchido em - $t4
		la $t4, trem
		encher_vagao:
			li $v0, 5
			syscall
			sw $v0, ($t4)
			
			# Passa para o próximo vagão
			addi $t4, $t4, 4
			# Incrementa o contado em 1 - $t3++
			addi $t3, $t3, 1		
			#Verifica se existe mais vagões para serem preenchidos
			blt $t3, $t2, encher_vagao
			
			# Voltando para o primeiro vagão do trem
			la $t4, trem
			
			# Cálculo para saber o último vagão
			li $t7, 4
			mul $t7, $t7, $t2	
			add $t7, $t4, $t7 		
	       	ordernar_trem:	       		
	       		# Reutilizando o contador - $t2	
			lw $t5, ($t4)			
			#Indo para o vagaão subsequente
			addi $t4, $t4, 4 	
			lw $t6, ($t4)
			
			# Verificando se o vagão anterior é maior que o vagão posterior
			beq $t4, $t7, fim_trem					
			bgt $t5, $t6, rotacao180
			j ordernar_trem
			
		rotacao180:
			sw $t5, ($t4)
			subi $t4, $t4, 4
			sw $t6, ($t4)
			
			la $t4, trem
			j ordernar_trem
			
		fim_trem:
			la $t4, trem
			print_trem:	
				li $v0, 1
				lw $a0, ($t4)
				syscall
				
				li $v0, 4
				la $a0, ligacao_vagoes
				syscall
				
				addi $t4, $t4, 4
				blt $t4, $t7, print_trem
				
		li $v0, 4
		la $a0, cabine_trem
		syscall	
		
		li $v0, 4
		la $a0, quebra_linha
		syscall
				
		addi $t8, $t8, 1
		blt $t8, $t0, preencher_trem
			
