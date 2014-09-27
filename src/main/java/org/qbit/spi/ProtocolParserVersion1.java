package org.qbit.spi;

import org.boon.Str;
import org.boon.collections.MultiMap;
import org.boon.core.reflection.FastStringUtils;
import org.boon.json.JsonParserAndMapper;
import org.boon.json.JsonParserFactory;
import org.boon.primitive.CharScanner;
import org.qbit.message.MethodCall;
import org.qbit.service.method.impl.MethodCallImpl;

import static org.boon.Exceptions.die;
import static org.qbit.service.Protocol.*;

/**
 * Created by Richard on 9/26/14.
 */
public class ProtocolParserVersion1 implements ProtocolParser {

    @Override
    public boolean supports(Object args, MultiMap<String, String> params) {

            return args instanceof String;
    }

    @Override
    public MethodCall<Object> parse(String address, String objectName, String methodName, Object args, MultiMap<String, String> params) {

        String name="";

        if (params!=null && params.size()>0) {

            if (params!=null && Str.isEmpty(methodName)) {
                name = params.get(METHOD_NAME_KEY);
            }

        }


        if (args!=null) {
            if (args instanceof String) {
                return processStringBody((String) args);
            }
        }

        return MethodCallImpl.method(name, address, args);


    }


    ThreadLocal<JsonParserAndMapper> jsonParserThreadLocal = new ThreadLocal<JsonParserAndMapper>() {
        @Override
        protected JsonParserAndMapper initialValue() {
            return new JsonParserFactory().create();
        }
    };


    private MethodCall<Object> processStringBody(String args) {
        final char[] chars = FastStringUtils.toCharArray(args);
        if (chars.length > 2 &&
                chars[PROTOCOL_MARKER_POSITION]
                        == PROTOCOL_MARKER) {

            final char versionMarker = chars[VERSION_MARKER_POSITION];

            if (versionMarker == PROTOCOL_VERSION_1) {
                return handleFastBodySubmissionVersion1Chars(chars);
            } else {
                die("Unsupported method call", args);
                return null;
            }
        } else {
            return jsonParserThreadLocal.get().parse(MethodCallImpl.class, chars);
        }
    }


    private MethodCall<Object> handleFastBodySubmissionVersion1Chars(char[] args) {

        int index=0;
        index++;
        index++;

        final char[][] chars = CharScanner.splitFromStartWithLimit(args,
                (char) PROTOCOL_SEPARATOR, index, 3);

        String address = FastStringUtils.noCopyStringFromChars(chars[
                ADDRESS_POS]);

        String returnAddress = FastStringUtils.noCopyStringFromChars(chars[
                RETURN_ADDRESS_POS]);


        String methodName = FastStringUtils.noCopyStringFromChars(chars[
                METHOD_NAME_POS]);


        String objectName = FastStringUtils.noCopyStringFromChars(chars[
                OBJECT_NAME_POS]);

        return null;

    }


}