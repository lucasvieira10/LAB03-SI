angular.module("toDoList", []);

angular.module("toDoList").controller("toDoListController", function($scope, agendaAPI, tarefaAPI, subtarefaAPI) {
	var self = $scope;

	self.agendas = [];
	self.tarefas = [];

    self.agendaAtual = new Agenda("", 0, 0, []);
    self.tarefaAtual = new Tarefa("", 0, false, "", "", []);

	self.prioridades = [1, 2, 3, 4];
	self.corDasPrioridades = ["Red", "Blue", "DarkOrange", "Green"];

	self.tarefasParaCumprirEmTodasAgendas = 0;
	self.agendaFoiSelecionada = false;

	self.carregarDadosDaAgendaAtual = function (agenda) {
        self.agendaAtual = agenda;
        _carregarTarefas();
	};

	self.atualizarTarefaAtual = function (tarefa) {
		self.tarefaAtual = tarefa;
    };

	self.salvarAgenda = function(nome) {
		var agenda = new Agenda(nome, 0, 0, []);

		agendaAPI.salvarAgenda(agenda).then(_carregarAgendas);
        delete self.agenda;

        self.agendaFoiSelecionada = false;
    };

	self.removerAgenda = function() {
		agendaAPI.removerAgenda(self.agendaAtual.id).then(_carregarAgendas);
        self.agendaFoiSelecionada = false;
	};

	self.excluirAgendas = function () {
		agendaAPI.removerAgendas().then(_carregarAgendas);
        self.agendaFoiSelecionada = false;
    };

	self.salvarTarefa = function(tarefa) {
        tarefa.id = Date.now();
        tarefaAPI.salvarTarefa(tarefa, self.agendaAtual.id).then(_carregarTarefas);

        self.agendaAtual.tarefasParaCumprir++;
        self.tarefaAtual = tarefa;

        delete self.tarefa;
        self.tarefaForm.$setPristine();

        _atualizarAgenda();
	};

	self.removerTarefa = function(tarefa) {
        tarefaAPI.removerTarefa(self.agendaAtual.id, tarefa.id).then(_carregarTarefas);
		_atualizarAgenda();
	};

	self.salvarSubtarefa = function (subtarefa) {
	    subtarefa.id = Date.now();
        subtarefaAPI.salvarSubtarefa(self.agendaAtual.id, self.tarefaAtual.id, subtarefa).then(_carregarSubtarefas);

        delete self.subtarefa;
    };

	self.removerSubtarefa = function (subtarefaID) {
        subtarefaAPI.removerSubtarefa(self.agendaAtual.id, self.tarefaAtual.id, subtarefaID).then(_carregarSubtarefas);
    };

	self.atualizarSubtarefa = function (subtarefa) {
        subtarefaAPI.atualizarSubtarefa(subtarefa, self.agendaAtual.id, self.tarefaAtual.id);
    };

	self.porcentagemDasTarefasConcluidas = function() {
		var porcentagem = (self.agendaAtual.tarefasJaConcluidas / self.tarefas.length) * 100;
		return parseFloat(porcentagem.toFixed(1));
	};

	self.estaVazia = function(tarefas) {
		return (tarefas.length > 0);
	};

	self.quantidadeDeTarefasDeUmaPrioridade = function(prioridade) {
		var quantidade = 0;

		for (var i in self.tarefas) {
			if (self.tarefas[i].prioridade === prioridade)
				quantidade++;
		}

		return quantidade;
	};

    self.quantidadeDeTarefasDeUmaCategoria = function(categoria) {
        var quantidade = 0;

        for (var i in self.tarefas) {
            if (self.tarefas[i].categoria === categoria)
                quantidade++;
        }

        return quantidade;
    };

    self.quantidadeDeTarefasFeitasDeUmaPrioridade = function(prioridade) {
        var quantidade = 0;

        for (var i in self.tarefas) {
            if (self.tarefas[i].prioridade === prioridade &&
                self.tarefas[i].selecionada === true)

                quantidade++;
        }

        return quantidade;
    };

    self.quantidadeDeTarefasFeitasDeUmaCategoria = function(categoria) {
        var quantidade = 0;

        for (var i in self.tarefas) {
            if (self.tarefas[i].categoria === categoria &&
                self.tarefas[i].selecionada === true)

                quantidade++;
        }

        return quantidade;
    };

	self.calcularTotalDeTarefasACumprir = function () {
	    self.tarefasParaCumprirEmTodasAgendas = 0;

        tarefaAPI.obterTarefasDasAgendas().then(function (tarefas) {
            for (var i in tarefas) {
                if (tarefas[i].selecionada == false)
                    self.tarefasParaCumprirEmTodasAgendas += 1;
            }
        });
    };

	self.limparTarefas = function() {
        _zerarValoresDaAgendaAtual();

        self.tarefas = [];

        tarefaAPI.removerTarefas(self.agendaAtual.id).then(_atualizarAgenda);
	};

	self.atualizarStatusDasTarefas = function() {
        _zerarValoresDaAgendaAtual();

		for (var i in self.tarefas) {
			if (_tarefaEstaSelecionada(self.tarefas[i]))
				self.agendaAtual.tarefasJaConcluidas++;
			else
				self.agendaAtual.tarefasParaCumprir++;
		}

        _atualizarAgenda();
	};

    self.atualizarTarefa = function() {
        tarefaAPI.atualizarTarefa(self.tarefaAtual, self.agendaAtual.id);
    };

    self.select = function(item) {
    	self.agendaFoiSelecionada = true;
        $scope.selected = item;
    };

    self.isActive = function(item) {
        return $scope.selected === item;
    };

    var _carregarAgendas = function () {
        agendaAPI.obterAgendas().then(function (agendas) {
            self.agendas = agendas;
        });
    };

    var _atualizarAgenda = function() {
        agendaAPI.atualizarAgenda(self.agendaAtual, self.agendaAtual.id).then(self.atualizarTarefa);
    };

    var _carregarTarefas = function () {
        tarefaAPI.obterTarefas(self.agendaAtual.id).then(function (tarefas) {
            self.tarefas = tarefas;

            self.atualizarStatusDasTarefas();
        });
    };

    var _carregarSubtarefas = function () {
        subtarefaAPI.carregarSubtarefas(self.agendaAtual.id, self.tarefaAtual.id).then(function (subtarefas) {
            self.tarefaAtual.subtarefas = subtarefas;
        });
    };

    var _tarefaEstaSelecionada = function(tarefa) {
        return tarefa.selecionada;
    };

    var _zerarValoresDaAgendaAtual = function() {
        self.agendaAtual.tarefasParaCumprir = 0;
        self.agendaAtual.tarefasJaConcluidas = 0;
    };

	_carregarAgendas();
});
