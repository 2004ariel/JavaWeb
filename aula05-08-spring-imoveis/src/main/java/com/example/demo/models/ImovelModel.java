package com.example.demo.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "imoveis")
@Getter
@Setter
public class ImovelModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    @Column(columnDefinition = "text")
    private String descricao;

    @Column(precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(precision = 10, scale = 2)
    private BigDecimal precoAluguel;

    private String finalidade;
    private String status;
    private Integer dormitorios;
    private Integer banheiros;
    private Integer garagem;

    @Column(precision = 10, scale = 2)
    private BigDecimal areaTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal areaConstruida;

    private String endereco;
    private String numero;
    private String complemento;
    private String cep;

    @Column(columnDefinition = "text")
    private String caracteristicas;

    private Boolean destaque;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_imovel_id")
    private TipoImovelModel tipoImovel;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bairro_id")
    private BairroModel bairro;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "senha"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserModel usuario;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ImovelModel() {}

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ImovelModel other = (ImovelModel) obj;
        if (id == null) return other.id == null;
        return id.equals(other.id);
    }
}
