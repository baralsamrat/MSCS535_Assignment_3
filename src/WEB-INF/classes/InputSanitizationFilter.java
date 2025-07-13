import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class InputSanitizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        HttpServletRequestWrapper sanitizedRequest = new HttpServletRequestWrapper(req) {
            private String sanitize(String value) {
                if (value == null) return null;
                return value.replaceAll("<", "&lt;")
                            .replaceAll(">", "&gt;")
                            .replaceAll("\"", "&quot;")
                            .replaceAll("'", "&#39;");
            }

            @Override
            public String getParameter(String name) {
                String value = super.getParameter(name);
                return sanitize(value);
            }

            @Override
            public String[] getParameterValues(String name) {
                String[] values = super.getParameterValues(name);
                if (values == null) return null;
                String[] sanitized = new String[values.length];
                for (int i = 0; i < values.length; i++) {
                    sanitized[i] = sanitize(values[i]);
                }
                return sanitized;
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                Map<String, String[]> paramMap = super.getParameterMap();
                Map<String, String[]> sanitizedMap = new HashMap<>();
                for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                    String[] values = entry.getValue();
                    String[] sanitized = new String[values.length];
                    for (int i = 0; i < values.length; i++) {
                        sanitized[i] = sanitize(values[i]);
                    }
                    sanitizedMap.put(entry.getKey(), sanitized);
                }
                return sanitizedMap;
            }
        };

        chain.doFilter(sanitizedRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {}
    @Override
    public void destroy() {}
}
