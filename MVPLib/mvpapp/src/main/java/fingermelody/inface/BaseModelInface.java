package fingermelody.inface;

/**
 * Created by FingerMelody on 2018/1/3.
 */

public interface BaseModelInface<P> {
    void bindPresenter(P presenter);

    P getPresenter();
}
