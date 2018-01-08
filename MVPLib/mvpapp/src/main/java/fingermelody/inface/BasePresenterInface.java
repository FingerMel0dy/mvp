package fingermelody.inface;

/**
 * Created by FingerMelody on 2018/1/3.
 */

public interface BasePresenterInface<V, M> {
    void bindView(V view);

    void bindMedel(M model);

    V getView();

    M getModel();

    boolean isViewBinded();

    void detachView();
}
