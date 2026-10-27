CreativeNoclip — projeto-fonte para Paper 26.3
=================================================

Comandos:
  /nc on   ativa voo Creative e tenta impedir movimento para dentro de blocos
  /nc off  desativa o modo do plugin (o voo é desligado)

Permissão:
  creativenoclip.use (padrão: OP)

IMPORTANTE:
Este projeto é uma aproximação usando a API pública do Paper, não um bypass real
de colisão como noclip de jogos. A API Bukkit/Paper comum não oferece atravessar
blocos sólidos de forma perfeita; o plugin cancela o movimento quando a posição
de destino entra num bloco sólido. Para atravessar paredes de verdade seria
necessário código interno/NMS específico da versão, com maior risco de quebrar.

Requisitos:
- Java 25
- Maven
- Paper 26.3

Como compilar:
1. Instale JDK 25 e Maven.
2. Abra um terminal nesta pasta.
3. Execute: mvn package
4. O JAR ficará em target/creative-noclip-1.0.0.jar
5. Coloque o JAR em plugins/ e reinicie o servidor.

Não é um JAR compilado; este ZIP contém o código-fonte do plugin.
