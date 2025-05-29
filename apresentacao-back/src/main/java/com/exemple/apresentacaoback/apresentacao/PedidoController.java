package com.exemple.apresentacaoback.apresentacao;

import com.exemple.implementacaoweb2.pedidos.Pedido;
import com.exemple.implementacaoweb2.pedidos.PedidoService;
import com.exemple.implementacaoweb2.pedidos.StatusPedido;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> contratarServico(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.contratarServico(pedido);
        return ResponseEntity.ok(novoPedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable int id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarPedido(@PathVariable int id) {
        pedidoService.atualizarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable int clienteId) {
        return ResponseEntity.ok(pedidoService.buscarPorClienteId(clienteId));
    }

    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<Pedido>> buscarPorPrestador(@PathVariable int prestadorId) {
        return ResponseEntity.ok(pedidoService.buscarPorPrestadorId(prestadorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> buscarPorStatus(@PathVariable StatusPedido status) {
        return ResponseEntity.ok(pedidoService.buscarPorStatus(status));
    }

    @PostMapping("/copiar/{idPedidoOriginal}")
    public ResponseEntity<Pedido> copiarPedidoComNovaData(@PathVariable Long idPedidoOriginal,
                                                          @RequestParam("novaData") String novaDataStr) {
        try {
            LocalDateTime novaData = LocalDateTime.parse(novaDataStr);
            Pedido novoPedido = pedidoService.criarNovoPedido(idPedidoOriginal, novaData);
            return ResponseEntity.ok(novoPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
