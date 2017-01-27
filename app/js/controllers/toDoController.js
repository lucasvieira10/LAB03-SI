angular.module("toDoList", []);

angular.module("toDoList").controller("toDoListController", function($scope, agendaAPI) {
	var self = $scope;

	self.tarefasParaCumprir = 0;
	self.tarefasJaConcluidas = 0;

	self.agendas = [];
	self.tarefas = [];

	self.prioridades = [1, 2, 3, 4];
	self.corDasPrioridades = ["Red", "Blue", "DarkOrange", "Green"];

	self.agendaAtual = null;
	self.tarefaAtual = null;

	self.tarefasParaCumprirEmTodasAgendas = 0;
	self.agendaFoiSelecionada = false;

	self.carregarDadosDaAgendaAtual = function (agenda) {
		agendaAPI.obterTarefas(agenda.id).then(function (tarefas) {
			self.tarefas = tarefas;
		});

		atualizarDadosDaAgendaAtual(agenda);
	};

	self.atualizarTarefaAtual = function (tarefa) {
		self.tarefaAtual = tarefa;
    };

	self.salvarAgenda = function(nome) {
		var agenda = {
			"nome": nome,
			"tarefasParaCumprir": 0,
			"tarefasJaConcluidas": 0,
			"tarefas": []
		};

		agendaAPI.salvarAgenda(agenda).then(carregarAgendas);
		delete self.agenda;
	};

	self.removerAgenda = function() {
		agendaAPI.removerAgenda(self.agendaAtual.id).then(carregarAgendas);

		self.tarefas = [];

		// self.agendaAtual = self.agendas[0];

		// self.agendaFoiSelecionada = false;
	};

	self.excluirAgendas = function () {
		agendaAPI.removerAgendas().then(carregarAgendas);
	};

	self.salvarTarefa = function(tarefa) {
        tarefa.id = Date.now();
        agendaAPI.salvarTarefa(tarefa, self.agendaAtual.id).then(carregarTarefas);

        self.tarefasParaCumprir++;
        self.tarefaAtual = tarefa;

        delete self.tarefa;
        self.tarefaForm.$setPristine();

        atualizarAgenda();
	};

	self.removerTarefa = function(tarefa) {
		agendaAPI.removerTarefa(self.agendaAtual.id, tarefa.id).then(carregarTarefas);

		atualizarAgenda();
	};

	self.porcentagemDasTarefasConcluidas = function() {
		var porcentagem = (self.tarefasJaConcluidas / self.tarefas.length) * 100;
		return parseFloat(porcentagem.toFixed(1));
	};

	self.estaVazia = function(tarefas) {
		return (tarefas.length > 0);
	};

	self.quantidadeDeTarefasDeUmaPrioridade = function(prioridade) {
		var quantidade = 0;

		for (var i in self.tarefas) {
			if (self.tarefas[i].prioridade === prioridade) {
				quantidade++;
			}
		}

		return quantidade;
	};

    self.quantidadeDeTarefasDeUmaCategoria = function(categoria) {
        var quantidade = 0;

        for (var i in self.tarefas) {
            if (self.tarefas[i].categoria === categoria) {
                quantidade++;
            }
        }

        return quantidade;
    };

    self.quantidadeDeTarefasFeitasDeUmaPrioridade = function(prioridade) {
        var quantidade = 0;

        for (var i in self.tarefas) {
            if (self.tarefas[i].prioridade === prioridade
				&& self.tarefas[i].selecionada === true) {
                quantidade++;
            }
        }

        return quantidade;
    };

    self.quantidadeDeTarefasFeitasDeUmaCategoria = function(categoria) {
        var quantidade = 0;

        for (var i in self.tarefas) {
            if (self.tarefas[i].categoria === categoria
                && self.tarefas[i].selecionada === true) {
                quantidade++;
            }
        }

        return quantidade;
    };

	self.calcularTotalDeTarefasACumprir = function () {
	    self.tarefasParaCumprirEmTodasAgendas = 0;

        agendaAPI.obterTarefasDasAgendas().then(function (tarefas) {
            for (var i in tarefas) {
                if (tarefas[i].selecionada == false) {
                    self.tarefasParaCumprirEmTodasAgendas += 1;
                }
            }
        });
    };



	self.limparTarefas = function() {
        self.tarefasParaCumprir = 0;
        self.tarefasJaConcluidas = 0;

        self.tarefas = [];

		agendaAPI.removerTarefas(self.agendaAtual.id).then(atualizarAgenda);
	};

	self.atualizarStatusDasTarefas = function() {
		self.tarefasParaCumprir = 0;
		self.tarefasJaConcluidas = 0;

		for (var i in self.tarefas) {
			if (tarefaEstaSelecionada(self.tarefas[i])) {
				self.tarefasJaConcluidas++;
			} else {
				self.tarefasParaCumprir++;
			}
		}

        atualizarAgenda();
	};

	self.resetarMudancasNaoSalvas = function () {
		carregarTarefas();

		// aqui eu deve chamar um metodo carregarTarefa()
		// que vai carregar somente aquela tarefa com o determinado id;
		// e setar ela na self.tarefas...
		console.log(self.tarefas);
    };

    self.atualizarTarefa = function() {
        agendaAPI.atualizarTarefa(self.tarefaAtual, self.agendaAtual.id);
    };

    self.select = function(item) {
    	self.agendaFoiSelecionada = true;

        $scope.selected = item;
    };

    self.isActive = function(item) {
        return $scope.selected === item;
    };

    var carregarAgendas = function () {
        agendaAPI.obterAgendas().then(function (agendas) {
            self.agendas = agendas;
        });
    };

    var atualizarDadosDaAgendaAtual = function (agenda) {
        self.tarefasParaCumprir = agenda.tarefasParaCumprir;
        self.tarefasJaConcluidas = agenda.tarefasJaConcluidas;

        self.agendaAtual = agenda;
    };

    var atualizarAgenda = function() {
    	self.agendaAtual.tarefasParaCumprir = self.tarefasParaCumprir;
    	self.agendaAtual.tarefasJaConcluidas = self.tarefasJaConcluidas;

        agendaAPI.atualizarAgenda(self.agendaAtual, self.agendaAtual.id).then(self.atualizarTarefa);
    };

    var carregarTarefas = function () {
        agendaAPI.obterTarefas(self.agendaAtual.id).then(function (tarefas) {
            self.tarefas = tarefas;

            self.atualizarStatusDasTarefas();
        });
    };

    var tarefaEstaSelecionada = function(tarefa) {
        return tarefa.selecionada;
    };

	carregarAgendas();
});
