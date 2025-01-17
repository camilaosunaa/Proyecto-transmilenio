    package com.example.demo.controller;

    import java.util.List;
    import java.util.stream.Collectors;

    import com.example.demo.DTO.ConductorDTO;
    import com.example.demo.modelo.Conductor;
    import com.example.demo.service.ServiceConductor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.annotation.Secured;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/conductor")
    public class ConductorController {

        @Autowired
        private ServiceConductor serviceConductor;

        @Secured({"Coordinador"})
        @GetMapping("/{idconductor}")
        public ResponseEntity<ConductorDTO> RecuperarConductor(@PathVariable Long idconductor){
            ConductorDTO conductorDTO = serviceConductor.getConductor(idconductor);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-type", "application/json")
                    .body(conductorDTO);
        }

        @Secured({"Coordinador"})
        @GetMapping
        public List<ConductorDTO> RecuperarConductores() {
            List<Conductor> conductores = serviceConductor.recuperarTodoConductor();

            // Convertir lista de conductores a lista de DTOs
            List<ConductorDTO> conductoresDTO = conductores.stream()
                    .map(conductor -> new ConductorDTO(
                            conductor.getId(),
                            conductor.getNombre(),
                            conductor.getCedula(),
                            conductor.getTelefono(),
                            conductor.getDireccion()))
                    .collect(Collectors.toList());

            return conductoresDTO;
        }

        @Secured({"Coordinador"})
        @PostMapping
        public ConductorDTO CrearConductor(@RequestBody ConductorDTO conductorDTO){
            return serviceConductor.createConductor(conductorDTO);
        }

        @Secured({"Coordinador"})
        @PutMapping("/{idConductor}")
        public ResponseEntity<ConductorDTO> actualizarConductor(@PathVariable Long idConductor, @RequestBody ConductorDTO conductorDTO) {
            try {
                ConductorDTO updatedConductor = serviceConductor.UpdateConductor(idConductor, conductorDTO);
                return ResponseEntity.status(HttpStatus.OK).body(updatedConductor);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
        }

        @Secured({"Coordinador"})
        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminarConductor(@PathVariable Long id) {
            try {
                serviceConductor.EliminarConductor(id);
                return ResponseEntity.ok("Conductor eliminado con éxito");
            }  catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al eliminar el conductor: " + e.getMessage());
            }
        }
    }
