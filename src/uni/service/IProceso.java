
package uni.service;

public interface IProceso<T> {
    // definir firma
    void Procesar(T o) throws Exception;
}
